import React, { useState, useEffect } from 'react';
import './CityServiceCarte.scss';
import axios from 'axios';
import { Card, CardBody, CardSubtitle, CardText, CardTitle } from 'reactstrap';
import Button from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const CityServiceCarte = ({ servicedata }: any) => {
  return (
    <div className="carte">
      <div className="logo">
        <FontAwesomeIcon icon={servicedata.icon} className="icon" />
      </div>
      <div className="title">{servicedata.title} </div>
    </div>
  );
};
export default CityServiceCarte;
