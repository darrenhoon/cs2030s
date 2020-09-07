import java.util.ArrayList;

Loader getNewLoader(int index, Cruise c) {
    return new Loader(index, c);
}

void serveCruises(Cruise[] cruises) {
    ArrayList<Loader> loaders = new ArrayList<Loader>();
    int loaderIndex = 0;

    for (Cruise c : cruises){ 
        int loadersRequired = c.getNumOfLoadersRequired();

        for(int i = 0; i<loaders.size();i++){
            Loader current_loader = loaders.get(i);
            
            if(current_loader.canServe(c)){
                Loader new_loader = current_loader.serve(c);
                System.out.println(new_loader);
                loaders.set(i, new_loader);
                loadersRequired--;
            }
        }
        System.out.println(loadersRequired);

        while(loadersRequired>0){
            loaders.add(getNewLoader(loaderIndex+1,c));
            System.out.println(loaders.get(loaderIndex));
            loaderIndex++;
            loadersRequired--;
        }
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
