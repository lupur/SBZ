import React from 'react';

import {Navbar, Container, Col} from 'react-bootstrap';

class Footer extends React.Component {
    render() {
        return (
            <Navbar fixed="bottom" bg="dark" variant="dark">
                <Container>
                    <Col lg={12} className="text-center text-muted">
                        <div>All Rights Reserved by Milos, Marina, Sava</div>
                    </Col>
                </Container>
            </Navbar>
        );
    }
}

export default Footer;