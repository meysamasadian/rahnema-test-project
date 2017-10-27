import {OPEN_POPOVER,CLOSE_POPOVER} from "../actions/popover_actions";
const initState = {
    visible: false,
    component: null
};
const popover = (state = initState, action) => {
    switch (action.type) {
        case OPEN_POPOVER:
            let newState =
                {
                    visible: true,
                    component: action.component
                };
            return Object.assign({},state,newState);
        case CLOSE_POPOVER:
            return Object.assign({},state,{
                visible: false,
                component: null
            });
        default:
            return state
    }
}

export default popover;