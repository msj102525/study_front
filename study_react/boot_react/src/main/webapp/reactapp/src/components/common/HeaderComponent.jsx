import React, { Component } from 'react';

class HeaderComponent extends Component {
    render() {
        return (
            <div>
                <div className="inner-header" style={{
                    border: "1px solid #000",
                    padding: "50px 0"
                    }}>
                    header
                </div>
            </div>
        );
    }
}

export default HeaderComponent;