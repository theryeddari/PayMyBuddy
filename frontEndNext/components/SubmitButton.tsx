// components/SubmitButtonDynamic.tsx
import React from 'react';

interface SubmitButtonDynamicProps {
    id: string;
    name: string;
    className?: string;
    onClick?: () => void; // Ajouter une fonction de gestion du clic en option
}

const SubmitButtonDynamic: React.FC<SubmitButtonDynamicProps> = ({
                                                                     id,
                                                                     name,
                                                                     className = '',
                                                                     onClick,
                                                                     children,
                                                                 }) => {
    return (

        <button
            id={id}
            name={name}
            className={`text-white px-4 sm:px-8 h-12 py-2 sm:py-3 bg-gray-300 hover:bg-gray-600 rounded min-w-min text-nowrap ${className}`}
            onClick={onClick} // Passer la fonction de gestion du clic

        >
            {children} {/* Afficher le contenu passé au bouton */}

        </button>
    );
};

export default SubmitButtonDynamic;