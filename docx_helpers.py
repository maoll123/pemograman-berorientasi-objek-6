import os
import sys
import re
import docx
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT, WD_ALIGN_VERTICAL
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import nsdecls, qn

def sanitize_xml_string(s):
    if not isinstance(s, str):
        s = str(s)
    # Remove XML-incompatible control characters except \t, \n, \r
    return re.sub(r'[\x00-\x08\x0B\x0C\x0E-\x1F\x7F]', '', s)

def set_cell_background(cell, fill_hex):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = parse_xml(f'<w:shd {nsdecls("w")} w:fill="{fill_hex}"/>')
    tcPr.append(shd)

def set_cell_margins(cell, top=120, bottom=120, left=180, right=180):
    tcPr = cell._tc.get_or_add_tcPr()
    tcMar = parse_xml(f'<w:tcMar {nsdecls("w")}><w:top w:w="{top}" w:type="dxa"/><w:bottom w:w="{bottom}" w:type="dxa"/><w:left w:w="{left}" w:type="dxa"/><w:right w:w="{right}" w:type="dxa"/></w:tcMar>')
    tcPr.append(tcMar)

def set_cell_borders(cell, top=None, bottom=None, left=None, right=None):
    tcPr = cell._tc.get_or_add_tcPr()
    borders_elm = OxmlElement('w:tcBorders')
    
    border_specs = {'top': top, 'bottom': bottom, 'left': left, 'right': right}
    for side, border in border_specs.items():
        if border:
            val = border.get('val', 'single')
            sz = border.get('sz', '4')
            color = border.get('color', 'D0D5DD')
            b_elm = parse_xml(f'<w:{side} {nsdecls("w")} w:val="{val}" w:sz="{sz}" w:space="0" w:color="{color}"/>')
            borders_elm.append(b_elm)
        else:
            b_elm = parse_xml(f'<w:{side} {nsdecls("w")} w:val="none"/>')
            borders_elm.append(b_elm)
    tcPr.append(borders_elm)

def add_callout(doc, text_content, title=None, fill_hex="EBF3FA", border_hex="102C57"):
    tbl = doc.add_table(rows=1, cols=1)
    tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    tbl.autofit = False
    
    cell = tbl.rows[0].cells[0]
    cell.width = Inches(6.8)
    set_cell_background(cell, fill_hex)
    set_cell_margins(cell, top=140, bottom=140, left=200, right=200)
    set_cell_borders(cell, 
                     top={'val': 'single', 'sz': '4', 'color': 'D0D5DD'},
                     bottom={'val': 'single', 'sz': '4', 'color': 'D0D5DD'},
                     right={'val': 'single', 'sz': '4', 'color': 'D0D5DD'},
                     left={'val': 'single', 'sz': '24', 'color': border_hex})
    
    p = cell.paragraphs[0]
    p.paragraph_format.space_before = Pt(2)
    p.paragraph_format.space_after = Pt(2)
    p.paragraph_format.line_spacing = 1.15
    
    if title:
        run_t = p.add_run(sanitize_xml_string(title) + "\n")
        run_t.font.name = "Calibri"
        run_t.font.size = Pt(10.5)
        run_t.font.bold = True
        run_t.font.color.rgb = RGBColor(16, 44, 87) # 102C57
        
    run_body = p.add_run(sanitize_xml_string(text_content))
    run_body.font.name = "Calibri"
    run_body.font.size = Pt(9.5)
    run_body.font.color.rgb = RGBColor(40, 40, 40)
    
    p_after = doc.add_paragraph()
    p_after.paragraph_format.space_before = Pt(0)
    p_after.paragraph_format.space_after = Pt(4)

def add_code_block(doc, file_name, code_text):
    p_label = doc.add_paragraph()
    p_label.paragraph_format.space_before = Pt(8)
    p_label.paragraph_format.space_after = Pt(2)
    run_icon = p_label.add_run("📄 ")
    run_icon.font.size = Pt(10)
    run_label = p_label.add_run(f"Listing Program {sanitize_xml_string(file_name)}")
    run_label.font.name = "Calibri"
    run_label.font.size = Pt(10)
    run_label.font.bold = True
    run_label.font.color.rgb = RGBColor(53, 89, 143) # 35598F
    
    tbl = doc.add_table(rows=1, cols=1)
    tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    tbl.autofit = False
    
    cell = tbl.rows[0].cells[0]
    cell.width = Inches(6.8)
    set_cell_background(cell, "F8F9FB")
    set_cell_margins(cell, top=120, bottom=120, left=160, right=160)
    set_cell_borders(cell, 
                     top={'val': 'single', 'sz': '4', 'color': 'D0D5DD'},
                     bottom={'val': 'single', 'sz': '4', 'color': 'D0D5DD'},
                     right={'val': 'single', 'sz': '4', 'color': 'D0D5DD'},
                     left={'val': 'single', 'sz': '18', 'color': '35598F'})
    
    p = cell.paragraphs[0]
    p.paragraph_format.space_before = Pt(0)
    p.paragraph_format.space_after = Pt(0)
    p.paragraph_format.line_spacing = 1.05
    
    run_code = p.add_run(sanitize_xml_string(code_text.strip()))
    run_code.font.name = "Consolas"
    run_code.font.size = Pt(8.5)
    run_code.font.color.rgb = RGBColor(30, 41, 59)
    
    p_after = doc.add_paragraph()
    p_after.paragraph_format.space_before = Pt(0)
    p_after.paragraph_format.space_after = Pt(6)

def add_terminal_output_block(doc, output_text):
    tbl = doc.add_table(rows=1, cols=1)
    tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    tbl.autofit = False
    
    cell = tbl.rows[0].cells[0]
    cell.width = Inches(6.8)
    set_cell_background(cell, "1E1E1E") # Dark terminal
    set_cell_margins(cell, top=140, bottom=140, left=180, right=180)
    set_cell_borders(cell, 
                     top={'val': 'single', 'sz': '6', 'color': '102C57'},
                     bottom={'val': 'single', 'sz': '6', 'color': '102C57'},
                     right={'val': 'single', 'sz': '6', 'color': '102C57'},
                     left={'val': 'single', 'sz': '24', 'color': '4CAF50'}) # Green accent terminal
    
    p = cell.paragraphs[0]
    p.paragraph_format.space_before = Pt(0)
    p.paragraph_format.space_after = Pt(0)
    p.paragraph_format.line_spacing = 1.05
    
    clean_text = sanitize_xml_string(output_text.strip())
    run_code = p.add_run(clean_text)
    run_code.font.name = "Consolas"
    run_code.font.size = Pt(8.0)
    run_code.font.color.rgb = RGBColor(220, 220, 220)
    
    p_after = doc.add_paragraph()
    p_after.paragraph_format.space_before = Pt(0)
    p_after.paragraph_format.space_after = Pt(6)

def format_heading(p, text, level):
    p.paragraph_format.keep_with_next = True
    clean_text = sanitize_xml_string(text)
    if level == 1:
        p.paragraph_format.space_before = Pt(14)
        p.paragraph_format.space_after = Pt(4)
        run = p.add_run(clean_text)
        run.font.name = "Calibri"
        run.font.size = Pt(14.0)
        run.font.bold = True
        run.font.color.rgb = RGBColor(16, 44, 87) # 102C57
    elif level == 2:
        p.paragraph_format.space_before = Pt(10)
        p.paragraph_format.space_after = Pt(3)
        run = p.add_run(clean_text)
        run.font.name = "Calibri"
        run.font.size = Pt(12.0)
        run.font.bold = True
        run.font.color.rgb = RGBColor(53, 89, 143) # 35598F
    elif level == 3:
        p.paragraph_format.space_before = Pt(8)
        p.paragraph_format.space_after = Pt(2)
        run = p.add_run(clean_text)
        run.font.name = "Calibri"
        run.font.size = Pt(10.5)
        run.font.bold = True
        run.font.color.rgb = RGBColor(31, 73, 125) # 1F497D

def format_body(p, text, bold_prefix=None):
    p.paragraph_format.space_before = Pt(2)
    p.paragraph_format.space_after = Pt(4)
    p.paragraph_format.line_spacing = 1.15
    if bold_prefix:
        r_pre = p.add_run(sanitize_xml_string(bold_prefix))
        r_pre.font.name = "Calibri"
        r_pre.font.size = Pt(10.0)
        r_pre.font.bold = True
        r_pre.font.color.rgb = RGBColor(40, 40, 40)
    r_body = p.add_run(sanitize_xml_string(text))
    r_body.font.name = "Calibri"
    r_body.font.size = Pt(10.0)
    r_body.font.color.rgb = RGBColor(40, 40, 40)

def create_table_styled(doc, headers, data, col_widths=None, align_center=False):
    tbl = doc.add_table(rows=len(data) + 1, cols=len(headers))
    tbl.alignment = WD_TABLE_ALIGNMENT.CENTER
    tbl.autofit = False
    
    # Header Row
    hdr_row = tbl.rows[0]
    for c_idx, h_text in enumerate(headers):
        cell = hdr_row.cells[c_idx]
        if col_widths and c_idx < len(col_widths):
            cell.width = Inches(col_widths[c_idx])
        set_cell_background(cell, "102C57")
        set_cell_margins(cell, top=100, bottom=100, left=140, right=140)
        set_cell_borders(cell, 
                         top={'val': 'single', 'sz': '6', 'color': '102C57'},
                         bottom={'val': 'single', 'sz': '12', 'color': '35598F'},
                         left={'val': 'single', 'sz': '4', 'color': '35598F'},
                         right={'val': 'single', 'sz': '4', 'color': '35598F'})
        p = cell.paragraphs[0]
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(0)
        if align_center or c_idx == 0:
            p.alignment = WD_ALIGN_PARAGRAPH.CENTER
        run = p.add_run(sanitize_xml_string(h_text))
        run.font.name = "Calibri"
        run.font.size = Pt(9.5)
        run.font.bold = True
        run.font.color.rgb = RGBColor(255, 255, 255)
        
    # Data Rows
    for r_idx, row_data in enumerate(data):
        row = tbl.rows[r_idx + 1]
        bg_color = "F8FAFC" if r_idx % 2 == 1 else "FFFFFF"
        for c_idx, val in enumerate(row_data):
            cell = row.cells[c_idx]
            if col_widths and c_idx < len(col_widths):
                cell.width = Inches(col_widths[c_idx])
            set_cell_background(cell, bg_color)
            set_cell_margins(cell, top=80, bottom=80, left=120, right=120)
            set_cell_borders(cell, 
                             top={'val': 'single', 'sz': '4', 'color': 'E2E8F0'},
                             bottom={'val': 'single', 'sz': '4', 'color': 'E2E8F0'},
                             left={'val': 'single', 'sz': '4', 'color': 'E2E8F0'},
                             right={'val': 'single', 'sz': '4', 'color': 'E2E8F0'})
            p = cell.paragraphs[0]
            p.paragraph_format.space_before = Pt(0)
            p.paragraph_format.space_after = Pt(0)
            p.paragraph_format.line_spacing = 1.1
            if align_center and c_idx == 0:
                p.alignment = WD_ALIGN_PARAGRAPH.CENTER
            run = p.add_run(sanitize_xml_string(val))
            run.font.name = "Calibri"
            run.font.size = Pt(9.0)
            run.font.color.rgb = RGBColor(40, 40, 40)
            if c_idx == 0 and ("SB-" in str(val) or str(val).isdigit()):
                run.font.bold = True
                
    p_after = doc.add_paragraph()
    p_after.paragraph_format.space_before = Pt(0)
    p_after.paragraph_format.space_after = Pt(4)
    return tbl
