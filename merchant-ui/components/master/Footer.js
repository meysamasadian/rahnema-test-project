import React,{Component} from 'react';
import PropTypes from 'prop-types';
import NewsAndApps from './NewsAndApps';
import LinkAndCopyRight from './LinkAndCopyRight';

const styles = {
    bar: {
        backgroundColor:'#000',
        opacity:.88,
        minHeight:205,
        bottom: 0,
        width: '100%'
    },
    logo: {
        cursor: 'pointer',
        paddingTop: 20
    },
};

export default class Footer extends Component {
    constructor(props){
        super(props);
    }

    render() {
        if (this.context.screenType === 'xs'
            || this.context.screenType === 'sm'
            || this.context.screenType === 'md'
        ) {
            Object.assign(styles.bar,{position: 'relative'});
        } else {
            Object.assign(styles.bar,{position: 'absolute'});
        }
        return <div className="footer" style={styles.bar} >
            <div className="container">
                <NewsAndApps />
                <LinkAndCopyRight />
            </div>
        </div>
    }
}

Footer.contextTypes = {
    screenType: PropTypes.string,
    width:PropTypes.number
};
