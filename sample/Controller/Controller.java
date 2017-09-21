package sample.Controller;

import sample.Maincontroller;

abstract public class Controller {

    protected Maincontroller maincontroller;

    public void setMaincontroller(Maincontroller maincontroller) {
        this.maincontroller = maincontroller;
    }

}
