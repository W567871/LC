# main solution

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
    
    count = 0
    for key in list(mappings):
      if (key in mappings):
          val = mappings[key]
          if (key==val):
              continue
          
          if (val in mappings and mappings[val] == key):
              count += 3;
              del mappings[val]
          else:
              count += 1
          del mappings[key]        
                
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

