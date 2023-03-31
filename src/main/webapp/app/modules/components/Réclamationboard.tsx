import React from 'react';
import './Réclamationboard.scss';
import { Nav, NavItem, NavLink } from 'reactstrap';
import CityServiceCarte from './CityServiceCarte';
import { useState } from 'react';

const Réclamationboard = () => {
  return (
    <>
      <div className="tab-container">
        <Nav fill pills>
          <NavItem>
            <NavLink active href="#">
              envoyer une réclamation
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="#">voir les réclamations</NavLink>
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
