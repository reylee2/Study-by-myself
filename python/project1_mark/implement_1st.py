## 实现方式1
import re
import block_content

title= True
f=open(file='test_input.txt')
fo=open(r'test_output1.html',mode='w')
partt=re.compile('\*(.+?)\*')

fo.write('<html><head><title>...</title><body>')

for i in block_content.blocks(f):
    i=re.sub(partt,r'<em>\1</em>',i)
    if title:
        fo.write('<h1>')
        fo.write(i)
        fo.write('</h1>')
        title=False
    else:
        fo.write('<p>')
        fo.write(i)
        fo.write('</p>')

fo.writelines('</body></html>')
f.close()
fo.close()
