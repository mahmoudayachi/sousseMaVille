import React, { useEffect } from 'react';
import './Reclamationboard.scss';
import { Nav, NavItem, NavLink, TabPane, Row, Container, TabContent } from 'reactstrap';
import ReclamationCategoryCard from './ReclamationCategoryCard';
import { useState } from 'react';
import axios from 'axios';
import Reclamationform from './Reclamationform';
import Reclamationhistorycontainer from './Reclamationhistorycontainer';

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
  const [categories, setcategories] = useState({});
  useEffect(() => {
    let categorydata = {};
    axios
      .get('http://localhost:8080/api/complaint-categories')
      .then(res => {
        categorydata = res.data;
        console.log(categorydata);
        setcategories(categorydata);
      })
      .catch(err => {
        console.log(err);
        categorydata = {};
      });
  }, []);
  return (
    <>
      <div className="tab-container">
        <Nav className="nav" fill pills>
          <NavItem className="item">
            <NavLink className="link" active={active} onClick={change} href="#">
              Signaler un probléme
            </NavLink>
          </NavItem>
          <NavItem className="item">
            <NavLink className="link" active={inactive} onClick={change} href="#">
              Voir réclamation
            </NavLink>
          </NavItem>
        </Nav>
      </div>
      {active == true && <Reclamationform categorydata={categories} />}
      {inactive == true && <Reclamationhistorycontainer />}
    </>
  );
};

export default Reclamationboard;
