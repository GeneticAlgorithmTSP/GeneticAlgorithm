import java.util.*;

public class Main {

    private static int[][] distance;
    private static int citieSize,chromosomeSize,trueSize=0;
    private static List<Integer> cities;
    private static List<Model> chromosomeParents, chromosomeChilds;
    private static List<Boolean> template;

    public static void main(String[] args) {
        init();
        CreateParent();

        int count = 0;
        CreateParent();
        for (int i=0;i<100000;i++){

            CreateChildren();
            ParentSelection();
            MutationInsert();
            ParentSelection();
            MutationReverse();
            ParentSelection();
/*
            System.out.println("Selection");
            for (Model m:chromosomeParents)
                System.out.println(m.cities+"\t"+m.solution);
            System.out.println("--------");
*/
            //System.out.println(i);
            count++;
        }

        System.out.println(count+".Iteration Mutation Insert");

        for (Model m:chromosomeParents)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("\nBest chromosome : \t"+GetBestChromosome(chromosomeParents).solution);
        System.out.println("--------");


        count = 0;
        CreateParent();
        for (int i=0;i<120000;i++){

            CreateChildren();
            //ParentSelection();



            for(int j=0; j<5; j++){

                MutationSwap();
                ParentSelection();
                MutationInsert();
                ParentSelection();
                MutationReverse();
                ParentSelection();
            }


/*
            System.out.println("Selection");
            for (Model m:chromosomeParents)
                System.out.println(m.cities+"\t"+m.solution);
            System.out.println("--------");
*/
            //System.out.println(i);
            count++;
        }

        System.out.println(count+".Iteration Mutation Swap");

        for (Model m:chromosomeParents)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("\nBest chromosome : \t"+GetBestChromosome(chromosomeParents).solution);
        System.out.println("--------");

    }

    private static void CreateChildren() {

        int index,crosCount=chromosomeSize/2;


        chromosomeChilds = new ArrayList<>();

        CreateRandomTemplate(trueSize);
        Model newChild1 = new Model();
        Model newChild2 = new Model();

        for(int k=0; k<citieSize; k++){

            newChild1.cities.add(-1000);
            newChild2.cities.add(-1000);
        }

        //CrossingOver
        for(int i=0;i<crosCount; i++){

            List<Integer> temp_parent1 = new ArrayList<>(chromosomeParents.get(2*i).cities);
            List<Integer> temp_parent2 = new ArrayList<>(chromosomeParents.get(2*i+1).cities);

            for(int j=0;j<citieSize; j++) {

                if (template.get(j)) {
                    newChild1.cities.set(j, chromosomeParents.get(2*i).cities.get(j));
                    temp_parent2.remove(chromosomeParents.get(2*i).cities.get(j));

                    newChild2.cities.set(j, chromosomeParents.get(2*i+1).cities.get(j));
                    temp_parent1.remove(chromosomeParents.get(2*i+1).cities.get(j));
                }
            }
            index = 0;
            for(int j=0;j<citieSize; j++) {

                if (!template.get(j)) {
                    newChild1.cities.set(j, temp_parent2.get(index));
                    newChild2.cities.set(j, temp_parent1.get(index));
                    index++;
                }
            }

            chromosomeChilds.add(new Model(newChild1));
            chromosomeChilds.add(new Model(newChild2));
        }

        CalculateSolition(chromosomeChilds);

        /*System.out.println("Children");
        for (Model m:chromosomeChilds)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("--------");
*/
    }

    private static Model GetBestChromosome(List<Model> chromosomes) {

        Model best = new Model();
        int minSolution = Integer.MAX_VALUE;

        for (Model m: chromosomes)
            if(m.solution < minSolution){

                minSolution = m.solution;
                best = m;
            }


        return best;
    }

    private static void CreateRandomTemplate(int zeroSize) {

        template = new ArrayList<>();

        for(int i=0; i<citieSize; i++){

            if(i<zeroSize)
                template.add(false);
            else
                template.add(true);

        }

        Collections.shuffle(template);
    }

    private static void init() {

        distance = new int[][]{

                {-1,89,32,86,33,57,66,32,69,80,90,64,29,36,78,33,88,89,45,87,76},
                {84,-1,61,61,61,30,60,53,53,85,50,89,43,73,28,45,29,75,51,86,72},
                {52,75,-1,76,62,79,88,67,32,57,28,53,74,42,89,26,37,90,34,55,72},
                {51,80,62,-1,36,29,86,31,75,59,41,78,81,60,78,69,84,32,79,64,90},
                {56,64,54,60,-1,67,65,79,76,79,45,39,73,66,60,58,74,69,39,50,32},
                {29,86,51,36,40,-1,76,71,76,44,37,62,86,46,70,66,46,29,65,65,48},
                {73,77,37,76,38,25,-1,87,27,25,81,29,53,32,74,47,81,55,55,50,64},
                {73,64,70,61,40,51,59,-1,61,77,53,55,53,60,86,80,61,85,73,66,84},
                {47,60,38,66,81,75,54,65,-1,69,56,53,59,76,55,49,33,52,44,52,40},
                {59,45,38,51,88,62,33,53,25,-1,42,76,30,55,68,45,53,51,32,39,30},
                {78,79,26,78,27,70,40,48,78,63,-1,41,62,41,79,67,82,40,76,31,52},
                {53,57,73,73,55,90,82,77,53,25,65,-1,46,49,56,49,27,76,71,29,81},
                {64,76,47,89,70,75,84,50,73,84,29,83,-1,29,25,52,88,52,59,74,59},
                {34,61,71,48,43,69,61,56,66,74,56,60,76,-1,25,32,83,72,52,39,66},
                {85,53,54,44,43,27,33,71,72,41,27,69,58,51,-1,70,32,40,26,48,87},
                {25,32,89,37,73,50,66,34,31,33,80,60,70,36,56,-1,27,71,73,69,56},
                {90,90,34,62,81,39,71,26,44,51,65,41,81,25,69,68,-1,82,86,33,41},
                {25,40,33,43,74,42,40,62,53,39,50,59,81,78,62,72,32,-1,86,31,64},
                {85,88,67,81,87,66,42,81,74,83,41,88,28,86,83,49,47,77,-1,47,32},
                {31,47,65,72,80,85,51,38,87,43,63,83,56,28,34,26,75,40,27,-1,68},
                {27,42,67,52,63,58,73,38,43,60,44,72,89,45,82,49,62,66,79,86,-1}
        };
/*
        distance = new int[][]{

                {-1,89,32,86,33},
                {84,-1,61,61,61},
                {52,75,-1,76,62},
                {51,80,62,-1,36},
                {56,64,54,60,-1}
        };
*/

        citieSize=distance.length;


        //sehirler
        cities = new ArrayList<>();

        for(int i=0; i<citieSize; i++)
            cities.add(i);

        //12 adet parent chromosomeozom olustur

        chromosomeSize = 12;
    }

    private static void CreateParent(){

        chromosomeParents = new ArrayList<>();
        //parent olusturma
        List<Integer> cities_temp = new ArrayList<>(cities);

        for (int i=0; i<chromosomeSize; i++){

            Collections.shuffle(cities_temp);

            Model m = new Model();
            m.cities.addAll(cities_temp);
            chromosomeParents.add(m);
        }
        CalculateSolition(chromosomeParents);


       /* System.out.println("Parents");
        for (Model m:chromosomeParents)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("--------");
*/
    }

    private static void CalculateSolition(List<Model> models){

        for (Model model: models) {

            int cost = 0;
            for(int i=0; i<citieSize-1; i++)
                cost += distance[model.cities.get(i)][model.cities.get(i+1)];
            model.solution = cost;
        }
    }

    private static void ParentSelection(){

        for (int i = 0; i < chromosomeSize; i++) {

            if(chromosomeParents.get(i).solution > chromosomeChilds.get(i).solution){

                boolean include = false;
                for (Model m:chromosomeParents)
                    if(m.solution == chromosomeChilds.get(i).solution)
                        include = true;

                if(!include) {
                    chromosomeParents.get(i).solution = chromosomeChilds.get(i).solution;
                    chromosomeParents.get(i).cities.clear();
                    chromosomeParents.get(i).cities.addAll(chromosomeChilds.get(i).cities);
                    //chromosomeParents.set(i,chromosomeChilds.get(i));

                    //Model model=chromosomeParents.get(i);
                    //System.out.println(model.cities+" \t Solution : "+model.solution);
                }
            }
        }
        /*System.out.println("Selection");
        for (Model m:chromosomeParents)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("--------");
    */
    }

    private static void MutationSwap(){

        Random random = new Random();

        for (Model m: chromosomeChilds) {

            int city1, city2;

            city1 = random.nextInt(citieSize);

            do{
                city2 = random.nextInt(citieSize);
            }while (city1 == city2);

            int temp = m.cities.get(city1);
            m.cities.set(city1,m.cities.get(city2));
            m.cities.set(city2,temp);
        }

        CalculateSolition(chromosomeChilds);


        /*System.out.println("Mutasyon");
        for (Model m:chromosomeChilds)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("--------");
*/
    }

    private static void MutationInsert(){

        Random random = new Random();

        for (Model m: chromosomeChilds) {

            int city1, city2;

            city1 = random.nextInt(citieSize);

            do{
                city2 = random.nextInt(citieSize);
            }while (city1 == city2);


            if(city1<city2){
                m.cities.add(city1+1,m.cities.get(city2));
                m.cities.remove(city2+1);
            }
            if(city1>city2){
                m.cities.add(city2+1,m.cities.get(city1));
                m.cities.remove(city1+1);
            }

        }

        CalculateSolition(chromosomeChilds);


        /*System.out.println("Mutasyon");
        for (Model m:chromosomeChilds)
            System.out.println(m.cities+"\t"+m.solution);
        System.out.println("--------");
*/
    }

    private static void MutationReverse(){
        Random random = new Random();

        for (Model m: chromosomeChilds) {

            int city1, city2;

            city1 = random.nextInt(citieSize);

            do{
                city2 = random.nextInt(citieSize);
            }while (city1 == city2);


            if(city1<city2){

                List<Integer> gecici=m.cities.subList(city1,city2);
                Collections.reverse(gecici);
            }
            else{

                List<Integer> gecici= m.cities.subList(city2,city1);
                Collections.reverse(gecici);
            }
        }

        CalculateSolition(chromosomeChilds);
    }
}