import React,{Component} from 'react';
import LogoContainer from './LogoContainer'

function handleTouchTap() {
    alert('onClick triggered on the title component');
}

const styles = {
    bar: {
        backgroundColor:'#fff',
        opacity:0.88,
        minHeight:60
    },
    logo: {
        cursor: 'pointer',
    },
};

export default class Header extends Component {


    render() {
        return <nav class="navbar bg-dark" style={styles.bar}>
            <div className="container">
                <div className="row">
                    <LogoContainer />
                </div>
            </div>
        </nav>
    }
}