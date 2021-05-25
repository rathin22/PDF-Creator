# PDF-Creator
This is a program used to render a PDF file using markdown type language. The markdown is entered in the *commands.txt* file and then when the program is ran, it will generate a pdf file *sample.pdf* based on what was entered in *commands.txt*

### Running the program
To run the program, open the PDF-Creator folder in VSCode, then navigate to *src -> main -> java -> pdfcreatorpackage -> App.java* and run this java file after entering the markdown in the *commands.txt* file.

### Available commands:
* **.paragraph**: starts a new paragraph
* **.newline**: adds a line break
* **.fill**: sets indentation to fill for paragraphs, where the last character of a line must end at the end of the margin (except for the last line of a paragraph)
* **.nofill**: the default, sets the formatter to regular formatting
* **.regular**: resets the font to the normal font
* **.italic**: sets the font to italic
* **.bold**: sets the font to bold
* **.large**: sets the font to size 24
* **.normal**: sets the font back to default size
* **.indent <number>**: indents the specified amount (each unit is probably about the length of the string “WWWW”)

### Example:
.bold <br/>
This text is bold. <br/>
.italic <br/>
This text is bold and italic. <br/>
.paragraph <br/>
.regular <br/>
.indent +2 <br/>
This indented text is regular <br/>
.indent -1 <br/>
This less indented text is regular

Result:

**This text is bold.**
***This text is bold and italic***

            This indented text is regular
     
      This non indented text is regular

<hr />

### Other general rules:
- Changing indent or fill automatically creates a new paragraph.
- Indent values are accumulative, not absolute
- All lines that start with a period "." are assumed to be a command. If you want to start your sentence with a period, you can do so by entering a backslash before the period "\\."

Pagination is automatically added in the PDF file. (*Page X of Y* in the top right corner)
