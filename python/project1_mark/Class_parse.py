## 实现一般方法类

import re,block_content
class Parse:
    def __init__(self,handle):
        self.handle=handle
        self.filters=[]
        self.rules=[]
    def addfilter(self,pattern,name):
        def filter(block,handle):
            return re.sub(pattern,handle.sub(name),block)
        self.filters.append(filter)
    def addrule(self,rule):
        self.rules.append(rule)
    def parse(self,file_in,file_out):
        file_out.write(self.handle.start('title'))
        for block in block_content.blocks(file_in):
            for filter in self.filters:
                block=filter(block,self.handle)
            for rule in self.rules:
                if rule.condition(block):
                    last,f_b=rule.action(block,self.handle)
                    if f_b:break
                    else:file_out.write(last)
            file_out.write(last)
        file_out.write(self.handle.end('title'))
