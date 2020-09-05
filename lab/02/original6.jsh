Loader getNewLoader(int index, Cruise c) {
    if(index%3==0 && index!=0){
        return new RecycledLoader(index,c);
    }
    else{
        return new Loader(index, c);
    }
}

void serveCruises(Cruise[] cruises) {
    final int MAX_LOADER = 9;
    final int MAX_CRUISE = 30;
    Loader[] loaders = new Loader[MAX_LOADER*MAX_CRUISE];
    int numOfLoaders = 0;

    for (Cruise c : cruises) {
        int count = c.getNumOfLoadersRequired();
        int i = 0;
        while (count > 0 && i < numOfLoaders) {
            if (loaders[i].canServe(c)) {
                loaders[i] = loaders[i].serve(c);
                System.out.println(loaders[i]);
                count--;
            }
            i = i + 1;
        }

        i = numOfLoaders;
        while (count > 0) {
            loaders[i] = getNewLoader(i + 1, c);
            System.out.println(loaders[i]);
            i = i + 1;
            count--;
        }

        numOfLoaders = i;
    }
}
