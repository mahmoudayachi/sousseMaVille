import React from 'react';
import './Reclamationhistorycard.scss';

const Reclamationhistorycard = () => {
  return (
    <>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="title">Vehicule ventouse</div>
          <div className="state">closed</div>
        </div>
        <div className="adresse">5 rue slayem cheyata</div>
        <div className="image">image</div>
      </div>
      <div className="history-carte">
        <div className="reclamation-description">
          <div className="title">Probleme d'eclairage </div>
          <div className="state">closed</div>
        </div>
        <div className="adresse">5 rue slayem cheyata</div>
        <div className="image">image</div>
      </div>
    </>
  );
};

export default Reclamationhistorycard;
