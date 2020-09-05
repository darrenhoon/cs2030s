import java.util.ArrayList;

Loader getNewLoader(int index, Cruise c) {
    if(index%3==0 && index!=0){
        return new RecycledLoader(index,c);
    }
    else{
        return new Loader(index, c);
    }
}

void serveCruises(Cruise[] cruises) {
    ArrayList<Loader> loaders = new ArrayList<Loader>();
    int loaderIndex = 0;

    for (Cruise c : cruises){ 
        int loadersRequired = c.getNumOfLoadersRequired();

        for(int i = 0; i<loaders.size();i++){
            if(loadersRequired<=0){
                break;
            }

            Loader current_loader = loaders.get(i);
            
            if(current_loader.canServe(c)){
                Loader new_loader = current_loader.serve(c);
                System.out.println(new_loader);
                loaders.set(i, new_loader);
                loadersRequired--;
            }
        }
        while(loadersRequired>0){
            loaders.add(getNewLoader(loaderIndex+1,c));
            System.out.println(loaders.get(loaderIndex));
            loaderIndex++;
            loadersRequired--;
        }
    }
}
