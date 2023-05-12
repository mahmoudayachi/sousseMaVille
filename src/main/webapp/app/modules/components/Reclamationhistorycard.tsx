import React, { useEffect } from 'react';
import './Reclamationhistorycard.scss';
import axios from 'axios';
const Reclamationhistorycard = ({ complaintdata }: any) => {
  return (
    <>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="title">{complaintdata.complaintCategory.name}</div>
          <div className="state">{complaintdata.complaintstate}</div>
        </div>
        <div className="adresse">{complaintdata.address}</div>

        <div className="description">{complaintdata.description}</div>
        <div className="image"></div>
      </div>
    </>
  );
};

export default Reclamationhistorycard;
