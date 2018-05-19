import java.util.ArrayList;
import java.util.List;

public class Model {

    List<Integer> cities = new ArrayList<>();
    int solution;

    Model(){ }

    Model(Model model){
        this.cities = new ArrayList<>(model.cities);
        this.solution = model.solution;
    }
}
