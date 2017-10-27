import React,{Component} from 'react';
import Header from './Header'
import Footer from './Footer'
import Dimensions from 'react-dimensions'
import PropTypes from 'prop-types';
import Content from "../content/Content";

class Master extends Component {

    constructor(props) {
        super(props);
    }

    getChildContext() {
        let size = this.props.containerWidth;
        if (size <= 575) {
            return {screenType: "xs", width: size};
        } else if (size >= 576 && size <= 767) {
            return {screenType: "sm", width: size};
        } else if (size >= 768 && size <= 991) {
            return {screenType: "md", width: size};
        } else if (size >= 992 && size <= 1199) {
            return {screenType: "lg", width: size};
        } else if (size >= 1200) {
            return {screenType: "xl", width: size};
        }
    }

    render() {
        return <div>
        <Header />
        <Content />
        </div>
    }
}

Master.childContextTypes = {
    screenType: PropTypes.string,
    width: PropTypes.number
};

export default Dimensions()(Master);