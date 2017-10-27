import React, {Component} from 'react';
import {CircularProgress, RadioButton, RadioButtonGroup, TextField} from "material-ui";
import axios from 'axios';

const styles = {
    block: {
        maxWidth: 250,
    },
    radioButton: {
        marginBottom: 16,
    },
};

export default class BasicStep extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.state = {
            data:undefined
        }
    }

    onChange(event, value) {
        this.props.callback(value);
    }


    async componentDidMount() {
        let that = this;
        let url = "http://localhost:4030/api/shops/";
        axios.get(url)
            .then(function(response){
                console.log("res" ,response);
                that.setState(Object.assign(that.state,{data:response.data}));
            }).catch(function(error){
            console.log("err", error)
        });
    }

    render() {
        const {
            data
        } = this.state;
        if (data) {
            let options = [];
            data.data.forEach(shop=>{
                options.push(<RadioButton
                    value={shop.pan}
                    label={shop.name}
                    style={styles.radioButton}
                />);
            });
            return <div className="form-inline">
                <div className="container">
                    <div className="row">
                        <div className="col-md-6 align-items-start">
                            <RadioButtonGroup name="shipSpeed" defaultSelected={this.props.shop} onChange={this.onChange}>
                                {options}
                            </RadioButtonGroup>
                        </div>
                    </div>
                </div>
            </div>
        } else {
            return <div className="form-inline">
                <div className="container">
                    <div className="row center">
                        <CircularProgress size={80} thickness={5} />
                    </div>
                </div>
            </div>
        }
    }
}