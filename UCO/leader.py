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
    for i,c in enumerate(breeds):
        if c == "G":
            Glist.append(i+1)
            if (lastG==0):
                firstG = i + 1
            lastG = i + 1
                        
        elif c == "H":
            Hlist.append(i+1)
            if (lastH == 0):
                firstH = i + 1
            lastH = i + 1 
        
    for gLeader in Glist:
        for hLeader in Hlist:

            success = True
            for i,r in enumerate(ranges):
                #if not a leader
                if (r!=gLeader and r!=hLeader):
                    if (r<i or r>len(ranges)):
                        success = False
                        break;

                # if r < i+1:
                #     success = False;
                #     break;

                #if a leader
                if (i+1==gLeader):
                    if not (r==hLeader or (r >= lastG and i+1 <= firstG)):
                        success = False
                        break;
                    
                if (i+1==hLeader):
                    if not (r==gLeader or (r >= lastH and i+1 <= firstH)):
                        success = False
                        break;


            # tmp = gLeader
            # gLeader = hLeader
            # hLeader = tmp
            # for i,r in enumerate(ranges):
            #     #if not a leader
            #     if (r!=gLeader and r!=hLeader):
            #         if (r<i or r>len(ranges)):
            #             break;

            #     #if a leader
            #     if (i==gLeader):
            #         if not (r==hLeader or r >= lastG):
            #             break;
                    
            #     if (i==hLeader):
            #         if not (r==gLeader or r >= lastH):
            #             break;
            if (success):
                results.append([gLeader, hLeader])
    # print("hi" + str(results))
    print(len(results))    
main();

