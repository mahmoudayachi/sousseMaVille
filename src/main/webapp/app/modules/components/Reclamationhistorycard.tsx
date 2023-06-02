import React, { useEffect, useState } from 'react';
import './Reclamationhistorycard.scss';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, Navigate } from 'react-router-dom';
import Reclamationdetails from './Reclamationdetails';
import ReclamationCategoryCard from './ReclamationCategoryCard';
import { Col, Row, Button } from 'reactstrap';

////
// <button className="button-supp" onClick={deletereclamation}>
//  <FontAwesomeIcon icon={'trash-can'}></FontAwesomeIcon>
//  </button>

// <FontAwesomeIcon icon={'location-dot'}></FontAwesomeIcon>
// <span className="ms-3">{complaintdata.address}</span>
// <br></br>
// <span className="ms-3">{complaintdata.description}</span>
//
const Reclamationhistorycard = ({ complaintdata }: any) => {
  const imagecontent = complaintdata.cityCitizenPhotos[0].image;
  const imagetype = complaintdata.cityCitizenPhotos[0].imageContentType;
  const imageurl = 'data' + ':' + imagetype + ';' + 'base64,' + imagecontent;

  // console.log(complaintdata);
  const deletereclamation = () => {
    axios
      .delete('http://localhost:8080/api/city-citizen-complaints/' + complaintdata.id)
      .then(response => {
        console.log(response);
        alert('réclamation supprimée');
      })
      .catch(error => console.log(error));
    window.location.reload();
  };
  //
  return (
    <div className="history-carte">
      <div className="grid-container">
        <div className="item1">
          <div> {complaintdata.complaintCategory.name}</div>
        </div>
        <div className="item2">
          <div className="state"> {complaintdata.complaintstate} </div>
        </div>
        <div className="item3">
          <img className="reclamation-picture" src={imageurl.toString()}></img>
        </div>
        <div className="item4">
          <div>{complaintdata.description}</div>
        </div>
        <div className="item5">
          <span>
            <FontAwesomeIcon icon={'location-dot'}></FontAwesomeIcon> {complaintdata.address}
          </span>
        </div>
        <div className="item6">
          <Button className="me-4" size="sm" color="primary">
            <Link className="white-text" to={'/Reclamationdetails'} state={complaintdata}>
              Consulter
            </Link>
          </Button>
          <Button size="sm" color="danger" onClick={deletereclamation}>
            <FontAwesomeIcon icon={'trash-can'}></FontAwesomeIcon>
            Supprimer
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Reclamationhistorycard;
