def lines(file):
    for f in file:
        yield f
    yield ''
def blocks(file):
    block=[]
    for l in lines(file):
        if l.strip():
            block.append(l)
        elif block:
            yield ''.join(block).strip()
            block=[]
