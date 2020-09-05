import java.util.ArrayList;

Loader getNewLoader(int index, Cruise c) {
    return new Loader(index, c);
}

void serveCruises(Cruise[] cruises) {
    ArrayList<Loader> loaders = new ArrayList<Loader>();
    int loaderIndex = 0;

    for (Cruise c : cruises) {
        int loadersRequired = c.getNumOfLoadersRequired();
        
        for(Loader loader: loaders){
            if (loader.canServe(c)){
                loader.serve(c);
                System.out.println(loader);
                loadersRequired--;
            }
        }

        for(i=0;i<loadersRequired;i++){
            loaders.add(getNewLoader(loaderIndex+1, c));
            System.out.println(getNewLoader(loaderIndex+1,c));
            LoaderIndex++;
        }
    }
}
