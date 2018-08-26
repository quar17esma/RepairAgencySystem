package com.quar17esma.controller.action;

import com.quar17esma.controller.action.impl.*;

/**
 * Possible Actions.
 */
public enum ActionEnum {
    LOGIN {
        {
            this.action = new Login();
        }
    },
    LOGOUT {
        {
            this.action = new Logout();
        }
    },
    ADD_USER {
        {
            this.action = new AddUser();
        }
    },
    EDIT_USER {
        {
            this.action = new EditUser();
        }
    },
    EDIT_APPLICATION {
        {
            this.action = new EditApplication();
        }
    },
    ADD_APPLICATION {
        {
            this.action = new AddApplication();
        }
    },
//    SHOW_MY_MEALS {
//        {
//            this.action = new ShowMyMeals();
//        }
//    },
    SHOW_APPLICATIONS {
        {
            this.action = new ShowApplications();
        }
    },
//    EDIT_FOOD {
//        {
//            this.action = new EditApplication();
//        }
//    },
//    ADD_FOOD {
//        {
//            this.action = new AddApplication();
//        }
//    },
//    SEARCH_FOOD {
//        {
//            this.action = new SearchFood();
//        }
//    },
    CHANGE_LOCALE {
        {
            this.action = new ChangeLocale();
        }
    };

    Action action;

    public Action getCurrentAction() {
        return action;
    }
}
