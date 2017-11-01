import React, {Component} from 'react';
import {CircularProgress, TextField} from "material-ui";
import axios from 'axios';



export default class PasswordStep extends Component {
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
        let url = "http://localhost:4030/controller/shops/products/"+this.props.shop+"/product/"+this.props.product+"/choose/";
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
            return <div className="form-inline">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 align-items-start">
                            <span>فروشگاه:</span>
                            <span style={{fontWeight:'bold'}}>{data.data.shop.name}</span>
                            <br/>
                            <span>محصول:</span>
                            <span style={{fontWeight:'bold'}}>{data.data.product.name},{data.data.product.price}</span>

                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12 align-items-start">
                            <TextField
                                className="col-md-12"
                                floatingLabelFixed=""
                                onChange={this.onChange}
                                hintText="شماره تلفن همراه"
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