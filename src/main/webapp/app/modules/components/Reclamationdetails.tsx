import React, { useState, useEffect } from 'react';
import './CityServiceCarte.scss';
import axios from 'axios';
import { Card, CardBody, CardSubtitle, CardText, CardTitle } from 'reactstrap';
import Button from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, useLocation } from 'react-router-dom';
import './Reclamationdetails.scss';

const Reclamationdetails = () => {
  const [data, setdata] = useState('');
  let imagetab = [];
  let location = useLocation();
  let state = location.state as any;
  for (let i = 0; i < state.cityCitizenPhotos.length; i++) {
    const imagecontent = state.cityCitizenPhotos[i].image;
    const imagetype = state.cityCitizenPhotos[i].imageContentType;
    const imageurl = 'data' + ':' + imagetype + ';' + 'base64,' + imagecontent;
    imagetab.push(imageurl);
    console.log(imagetab);
  }

  return (
    <div className="main-container">
      <div className="container-image">
        {imagetab.map((image, id) => (
          <img className="imgg" src={image.toString()} />
        ))}
      </div>
      <div className="information">
        <span>Categorie: {state.complaintCategory.name}</span>
        <br></br>
        <span>Description: {state.description}</span>
        <br></br>
        <span>Date d'envoi: {state.date}</span>
        <br></br>
        <span>State: {state.complaintstate}</span>
        <br></br>
        <span>Lieu: {state.address}</span>
      </div>
    </div>
  );
};

export default Reclamationdetails;
