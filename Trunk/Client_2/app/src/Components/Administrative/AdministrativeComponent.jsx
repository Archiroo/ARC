import React, {Component} from "react"

class AdministrativeComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            administratives: []
        }
    }
    
    render() {
        return (
            <div>
                <h2 className="text-center">List Administrative</h2>
                <div className="row">
                    <table className="table table-striped table-bordered">

                    </table>
                </div>
            </div>
        )
    }

}
export default AdministrativeComponent