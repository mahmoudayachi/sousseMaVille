import React from 'react';
import './Réclamationboard.scss';
import { Nav, NavItem, NavLink, TabPane, Row, Container, TabContent } from 'reactstrap';
import CityServiceCarte from './CityServiceCarte';
import { useState } from 'react';

const Réclamationboard = () => {
  const [active, Setactive] = useState(false);
  const [inactive, Setinaactive] = useState(true);
  const change = () => {
    if (active == false) {
      Setinaactive(false);
      Setactive(true);
    } else if (inactive == false) {
      Setinaactive(true);
      Setactive(false);
    }
  };
  return (
    <>
      <div className="tab-container">
        <Nav className="nav" fill pills>
          <NavItem className="item">
            <NavLink className="link" active={active} onClick={change} href="#">
              envoyer une réclamation
            </NavLink>
          </NavItem>
          <NavItem className="item">
            <NavLink className="link" active={inactive} onClick={change} href="#">
              voir réclamation
            </NavLink>
          </NavItem>
        </Nav>
      </div>
      <div className="réclamation-cards-container">
        <div className="réclamation-cards">
          <div>hello</div>
        </div>
      </div>
    </>
  );
};

export default Réclamationboard;
