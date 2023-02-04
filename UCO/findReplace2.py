# main solution
class DisjointSet(object):

    def __init__(self):
        self.leader = {} # maps a member to the group's leader
        self.group = {} # maps a group leader to the group (which is a set)

    def add(self, a, b):
        leadera = self.leader.get(a)
        leaderb = self.leader.get(b)
        if leadera is not None:
            if leaderb is not None:
                if leadera == leaderb: return # nothing to do
                groupa = self.group[leadera]
                groupb = self.group[leaderb]
                if len(groupa) < len(groupb):
                    a, leadera, groupa, b, leaderb, groupb = b, leaderb, groupb, a, leadera, groupa
                groupa |= groupb
                del self.group[leaderb]
                for k in groupb:
                    self.leader[k] = leadera
            else:
                self.group[leadera].add(b)
                self.leader[b] = leadera
        else:
            if leaderb is not None:
                self.group[leaderb].add(a)
                self.leader[a] = leaderb
            else:
                self.leader[a] = self.leader[b] = a
                self.group[a] = set([a, b])



def convert(str1:str, str2:str) -> int:
    if str1 == str2:
      return 0

    mappings = {}

    # No char in str1 can be mapped to > 1 char in str2
    for a, b in zip(str1, str2):
      if mappings.get(a, b) != b:
        return -1
      mappings[a] = b

    # No char in str1 maps to > 1 char in str2 and
    # There is at lest one temp char can break any loops
    if (len(set(str2)) >= 52):
        return -1;
    
    
    ds = DisjointSet()
    
    twoElemCycCt = 0        
    count = 0
    for key, value in mappings.items():
        if (key == value):
            continue;
        count += 1
        if (mappings.get(value) == key):
            twoElemCycCt +=1
            continue
        ds.add(key, value)
        
    twoElemCycCt = twoElemCycCt // 2
    mulElemCycCt = 0
    for key, value in ds.group.items():
        if (len(value) > 2):
            mulElemCycCt += 1
    
    count = count + twoElemCycCt + mulElemCycCt
                    
    return count
    

def main():
    # read the total inputs
    total = int(input()) 

    results = []
    for _ in range(total):
        inStr = input()
        outStr = input()
        results.append(convert(inStr, outStr))

    for result in results:
        print(result)
        
main();

