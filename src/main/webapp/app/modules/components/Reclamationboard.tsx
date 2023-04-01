import React from 'react';
import './Reclamationboard.scss';
import { Nav, NavItem, NavLink, TabPane, Row, Container, TabContent } from 'reactstrap';
import ReclamationCategoryCard from './ReclamationCategoryCard';
import { useState } from 'react';

const Reclamationboard = () => {
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
            <NavLink className="link" active={active} onClick={change} href="/reclamationform">
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
        <ReclamationCategoryCard />
      </div>
    </>
  );
};

export default Reclamationboard;
