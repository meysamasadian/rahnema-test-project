import { connect } from 'react-redux'
import { closePopover } from '../redux/actions/popover_actions'
import MyDialog from '../components/dashboard/MyDialog'


const getPopoverState = (popover) => {
    return {
        visible:popover.visible,
        component: popover.component
    };
}

const mapStateToProps = (state) => (
    {
    popover: getPopoverState(state.popover)
    }
);

const mapDispatchToProps = {
    dialogOnClick: closePopover
}


const DialogContainer = connect(mapStateToProps,mapDispatchToProps)(MyDialog);
export default DialogContainer;
