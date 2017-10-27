import React, {Component} from 'react';
import AddPerson from 'material-ui/svg-icons/social/person-add';
import LockIcon from 'material-ui/svg-icons/action/lock-open';
import FlatButton from 'material-ui/FlatButton';



export default class RememberAndRegisterRequest extends Component {

    render() {
        return <div className="row align-items-start">
            <div className="col-8"></div>
            <div class="form-inline col">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col">
                            <FlatButton
                                label="ثبت نام"
                                labelPosition="before"
                                primary={true}
                                icon={<AddPerson />}
                            />
                        </div>
                        <div className="col">
                            <FlatButton
                                label="فراموشی رمز"
                                labelPosition="before"
                                primary={true}
                                icon={<LockIcon />}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>;
    }
}