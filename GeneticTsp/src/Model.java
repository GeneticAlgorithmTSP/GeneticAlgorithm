import java.util.ArrayList;
import java.util.List;

public class Model {

    List<Integer> cities;
    int solution;

    Model(){

        cities = new ArrayList<>();
        solution = 0;
    }

    Model(Model model){
        this.cities = new ArrayList<>(model.cities);
        this.solution = model.solution;
    }
}
