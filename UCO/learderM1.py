#solution function:
def main():
    #write a template to execute a brute force solution
    #read the number of cows
    n = int(input())

    #read the breeds of cows
    breeds = input()

    #read the ranges of cows
    ranges = list(map(int, input().split())) #[2,4,3,4]

    Glist = []
    Hlist = []
    
    results = []
    lastG = 0
    lastH = 0
    
    if (breeds[0]=="G"):
        gStart = True;
    elif (breeds[0]=="H"):
        gStart = False;
    else:
        print(0);
        return;    
    
    firstH = 0;
    firstG = 0;
    for i,c in enumerate(breeds):
        if c == "G":
            if (not gStart):
                if (firstG == 0): 
                    Glist.append(i+1);
            elif (firstH == 0):
                Glist.append(i+1);    
                        
            if (lastG==0):
                firstG = i + 1
            lastG = i + 1
                        
        elif c == "H":
            if (gStart):
                if (firstH == 0):
                    Hlist.append(i+1)
            elif (firstG == 0):
                Hlist.append(i+1);
                        
            if (lastH == 0):
                firstH = i + 1
            lastH = i + 1 
            
        
    for gLeader in Glist:
        for hLeader in Hlist:

            success = True
            gIdx = gLeader - 1
            hIdx = hLeader - 1
            
            # check gLeader
            if not (ranges[gIdx] >= hLeader or (ranges[gIdx] >= lastG and gLeader <= firstG)):
                success = False
            
            # check hLeader
            if not (ranges[hIdx] >= gLeader or (ranges[hIdx] >= lastH and hLeader <= firstH)):
                success = False
            
            
            if (success):
                results.append([gLeader, hLeader])
    # print("hi" + str(results))
    print(len(results))    
main();

