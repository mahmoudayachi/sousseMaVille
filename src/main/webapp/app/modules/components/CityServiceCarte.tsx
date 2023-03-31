import React, { useState, useEffect } from 'react';
import './CityServiceCarte.scss';
import axios from 'axios';
import { Card, CardBody, CardSubtitle, CardText, CardTitle } from 'reactstrap';
import Button from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';

const CityServiceCarte = ({ servicedata }: any) => {
  return (
    <div className="carte">
      <div className="logo">
        <FontAwesomeIcon icon={servicedata.icon} className="icon" />
      </div>
      <div className="title">{servicedata.title} </div>
      <div className="description">{servicedata.description}</div>
      {servicedata.title == 'réclamation' && <Link to="/réclamationboard">lien</Link>}
    </div>
  );
};
export default CityServiceCarte;
