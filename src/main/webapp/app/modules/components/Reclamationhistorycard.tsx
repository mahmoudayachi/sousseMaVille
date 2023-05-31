import React, { useEffect, useState } from 'react';
import './Reclamationhistorycard.scss';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, Navigate } from 'react-router-dom';
import Reclamationdetails from './Reclamationdetails';
import ReclamationCategoryCard from './ReclamationCategoryCard';

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
    <>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="contenu-description"> {complaintdata.complaintCategory.name}</div>
          <div className="state"> {complaintdata.complaintstate} </div>
        </div>
        <div className="adresse">
          <FontAwesomeIcon icon={'location-dot'}></FontAwesomeIcon>
          <span className="ms-3">{complaintdata.address}</span>
          <div className="details">
            <Link to={'/Reclamationdetails'} state={complaintdata}>
              consulter
            </Link>
          </div>
        </div>

        <div className="supp-button-container">
          
          <img className="reclamation-picture" src={imageurl.toString()}></img>
          <button className="button-supp" onClick={deletereclamation}>
            <FontAwesomeIcon icon={'trash-can'}></FontAwesomeIcon>
          </button>
        </div>
        
       
          
      </div>
    </>
  );
};

export default Reclamationhistorycard;
