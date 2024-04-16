package view;

import static controller.Constants.ENEMY_SIDE_LENGTH;

public class SquarantineView extends EntityView {

    double sideLength = ENEMY_SIDE_LENGTH;

    public SquarantineView(String id) {
        super(id);
    }
}
