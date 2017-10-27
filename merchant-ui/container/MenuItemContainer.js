import React from 'react'
import { connect } from 'react-redux'
import { openPopover } from '../redux/actions/popover_actions'
import {DMenuItem} from "../components/dashboard/DMenu";


let MenuItemContainer = ({dispatch,title, icon, component, menuItems}) => {
    if (menuItems) {
        let customMenu = menuItems.map((menuItem)=>{
            let iOnClick = (event) => {
                event.preventDefault();
                menuItem.props.dispatch(openPopover(menuItem.props.component));
            };
            return <DMenuItem icon={menuItem.props.icon} title={menuItem.props.title} onClick={iOnClick}/>
        });
        return <DMenuItem icon={icon} title={title} menuItems={customMenu}/>
    } else {
        let onClick = (event) => {
            event.preventDefault();
            dispatch(openPopover(component));
        };
        return <DMenuItem icon={icon} title={title} onClick={onClick}/>
    }
}

MenuItemContainer = connect()(MenuItemContainer);
export default MenuItemContainer;

