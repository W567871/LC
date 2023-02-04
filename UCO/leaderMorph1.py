# main solution
def main():
    # read the total cows
    total = int(input())

    #read the cows string 
    cows = input()

    #read the cowList of cows
    cowList = list(map(int, input().split())) 

    candidateGLeaders = []
    candidateHLeaders = []

    hCowStart = 0;
    gCowStart = 0;
    gCowEnd = 0
    hCowEnd = 0
   
    if (cows[0]=="G"):
        isHeadG = True;
    else:
        isHeadG = False;
    
    for i,cow in enumerate(cows):
        if cow == "H":
            if (isHeadG):
                if (hCowStart == 0):
                    candidateHLeaders.append(i+1)
            elif (gCowStart == 0):
                candidateHLeaders.append(i+1);
                        
            if (hCowEnd == 0):
                hCowStart = i + 1
            hCowEnd = i + 1 
        else:
            if (not isHeadG):
                if (gCowStart == 0): 
                    candidateGLeaders.append(i+1);
            elif (hCowStart == 0):
                candidateGLeaders.append(i+1);    
                        
            if (gCowEnd==0):
                gCowStart = i + 1
            gCowEnd = i + 1
                                    
    leadPairCount = 0    
    for candidateGLeader in candidateGLeaders:
        for candidateHLeader in candidateHLeaders:

            isValidLeader = True
            candidateGPosition = candidateGLeader - 1
            candidateHPosition = candidateHLeader - 1

            # check candidateHLeader
            if not (cowList[candidateHPosition] >= candidateGLeader or (cowList[candidateHPosition] >= hCowEnd and candidateHLeader <= hCowStart)):
                isValidLeader = False
                continue;            
                        
            # check candidateGLeader
            if not (cowList[candidateGPosition] >= candidateHLeader or (cowList[candidateGPosition] >= gCowEnd and candidateGLeader <= gCowStart)):
                isValidLeader = False
                continue;
            
            if (isValidLeader):
                leadPairCount = leadPairCount + 1


    print(leadPairCount)    
        
main();

