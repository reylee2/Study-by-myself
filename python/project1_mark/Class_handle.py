## 创建对内容块的处理方法

class Handle:
    def callback(self,prefix,name,*args):
        method=getattr(self,prefix+name,None)
        if callable(method):return method(*args)
    def start(self,name):
        return self.callback('start_',name)
    def end(self,name):
        return self.callback('end_',name)
    def sub(self,name):
        def substitution(match):
            result=self.callback('sub_',name,match)
            if result is None: return match.group(0)
            return result
        return substitution

class HtmlContent(Handle):
    def start_title(self):
        title=input('Please input title name: ')
        return '<html><head><title>%s</title><body>' % title
    def end_title(self):
        return '</body></html>'
    def start_header(self):
        return '<h1>'
    def end_header(self):
        return '</h1>'
    def start_subheader(self):
        return '<h2>'
    def end_subheader(self):
        return '</h2>'
    def start_list(self):
        return '<ul>'
    def end_list(self):
        return '</ul>'
    def start_listitem(self):
        return '<li>'
    def end_listitem(self):
        return '</li>'
    def start_paragraph(self):
        return '<p>'
    def end_paragraph(self):
        return '</p>'
    def sub_emphasis(self,match):
        return '<em>%s</em>' % match.group(1)
    def sub_url(self,match):
        return '<a href="%s">%s</a>' % (match.group(1),match.group(1))
    def sub_email(self,match):
        return '<a href="mailto:%s">%s</a>' % (match.group(1),match.group(1))
    def feed(self,data):
        return ''.join(data).strip()
