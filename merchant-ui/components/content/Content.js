import React,{Component} from 'react';
import PropTypes from 'prop-types';
import RegisterStepper from "./RegisterStepper";


const styles = {
    root: {
        width:'50%',
        minHeight:'300px',
        top:10,
        right: 10,
        backgroundColor: '#fff',
        opacity: '0.88'
    }
}

export default class Content extends Component {
    constructor(props){
        super(props);
    }

    render() {
        let width = this.context.width;
        let screenType = this.context.screenType;
        switch (screenType) {
            case 'xl':
                Object.assign(styles.root,{
                    position:'fixed',
                    width : width/2,
                    right : width/4,
                    top : '25%'
                });
                break;
            case 'lg':
                Object.assign(styles.root,{
                    position:'fixed',
                    width : width*2/3,
                    right : width/6,
                    top : '25%'
                });
                break;
            case 'md':
            case 'sm':
            case 'xs':
                Object.assign(styles.root,{
                    borderTop: '1px solid #777',
                    position:'relative',
                    width : '100%',
                    right : 0,
                    top : 0
                });
                break;
        }
        return <div style={styles.root}>
            <div className="register-container-inner">
                <div className="container-fluid">
                    <div className="row">
                        <div className="col">
                            <RegisterStepper />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    }
}

Content.contextTypes = {
    screenType: PropTypes.string,
    width:PropTypes.number
};
