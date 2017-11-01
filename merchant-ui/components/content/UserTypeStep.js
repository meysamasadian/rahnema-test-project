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

export default class UserTypeStep extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.state= {
            data:undefined
        }
    }

    onChange(event, value) {
        this.props.callback(value);
    }

    async componentDidMount() {
        let that = this;
        const {
            shop,
            product,
            purchaser
        } = this.props;
        let url = "http://localhost:4030/controller/shops/login/";
        let data = {"shop":shop,"productCode":product,"purchaser":purchaser};
        axios.post(url,data)
            .then(function(response){
                console.log("res" ,response);
                that.setState(Object.assign(that.state,{data:response.data}));
            }).catch(function(error){
            console.log("err", error);
            that.props.errorCallback(error.response.data.view);
        });
    }

    render() {
        const {
            data
        } = this.state;
        if (data) {
            return <div className="form-inline">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 align-items-start">
                            <span style={{fontWeight:'bold', fontSize:'16px'}}>{data.data.otp.data}</span>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12 align-items-start">
                            <TextField
                                className="col-md-12"
                                floatingLabelFixed=""
                                onChange={this.onChange}
                                hintText="یک بار رمز"
                            />
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