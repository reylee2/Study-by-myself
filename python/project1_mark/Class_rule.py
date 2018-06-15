## 内容块实现方法类

class Rule:
    def action(self,block,handle):
        result=[]
        result.append(handle.start(self.type))
        result.append(handle.feed(block))
        result.append(handle.end(self.type))
        return ''.join(result).strip(),True

class SubHeaderRule(Rule):
    type='subheader'
    def condition(self,block):
        return not '\n' in block and len(block)<=70 and block[-1]!=':'

class HeaderRule(Rule):
    type='header'
    first=True
    def condition(self,block):
        if self.first:
            self.first=False
            return SubHeaderRule.condition(self,block)
        else: return False

class ListItemRule(Rule):
    type='listitem'
    def condition(self,block):
        return block[0]=='-'
    def action(self,block,handle):
        result=[]
        result.append(handle.start(self.type))
        result.append(handle.feed(block[1:]))
        result.append(handle.end(self.type))
        return ''.join(result).strip(),True

class ListRule(ListItemRule):
    type='list'
    bool=False
    def condition(self,block):
        return True
    def action(self,block,handle):
        mid=''
        if not self.bool and ListItemRule.condition(self,block):
            mid=handle.start(self.type)
            self.bool=True
        if self.bool and not ListItemRule.condition(self,block):
            mid=handle.end(self.type)
            self.bool=False
        return mid,False

class ParagraphRule(Rule):
    type='paragraph'
    def condition(self,block):
        return True
