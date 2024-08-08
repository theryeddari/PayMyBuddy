"use client"
// Types des données du DTO
import React, {createContext, ReactNode, useCallback, useContext, useEffect, useState} from "react";
import {useApi} from "@/util/FetchBackEnd";

interface TransferredGeneralDetailDTO {
    receiverEmail: string;
    description: string;
    amount: number;
}

interface TransferredGeneralDetailResponse {
    listTransferredSuccesses: TransferredGeneralDetailDTO[];
}

interface SavingClientResponse {
    saving: number;
}

interface RelationShipsDetailForTransferResponse {
    listFriendsRelationShipsEmail: string[];
}

interface AggregationNecessaryInfoForTransferResponse {
    transferredGeneralDetailResponse: TransferredGeneralDetailResponse;
    relationShipsDetailForTransferResponse: RelationShipsDetailForTransferResponse;
    savingClientResponse: SavingClientResponse;
}

// Contexte
interface TransferContextProps {
    data: AggregationNecessaryInfoForTransferResponse | null;
    setData: React.Dispatch<React.SetStateAction<AggregationNecessaryInfoForTransferResponse | null>>;
    loadData: () => void;
    isLoading: boolean;
    error: Error | null;
}

const AggregationContext = createContext<TransferContextProps | undefined>(undefined);

interface AggregationProviderProps {
    children: ReactNode;
}

export const AggregationProvider: React.FC<AggregationProviderProps> = ({children}) => {
    const {data, fetchFromApi} = useApi<AggregationNecessaryInfoForTransferResponse>();
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<Error | null>(null); // État pour l'erreur

    // Création d'une méthode loadData pour charger les données
    const loadData = useCallback(async () => {
        setIsLoading(true);
        try {
            await fetchFromApi('http://localhost:8080/api/fr/client/dashboard/transfer', {method: 'GET'});
        } catch (e) {
            if (e instanceof Error) {
                setError(e); // Conserver l'instance d'Error
            } else {
                setError(new Error('Une erreur inconnue est survenue'));
            }
        }
    }, [fetchFromApi]);

    // Chargement des données lors du premier rendu
    useEffect(() => {
        if (!isLoading) {
            loadData();
        }
    }, [isLoading, loadData]);

    return (
        <AggregationContext.Provider value={{
            data, setData: () => {
            }, loadData, isLoading, error
        }}>
            {children}
        </AggregationContext.Provider>
    );
};

// Hook personnalisé pour utiliser le contexte
export const useAggregation = (): TransferContextProps => {

    const context = useContext(AggregationContext);
    if (context === undefined) {
        throw new Error('useAggregation must be used within an AggregationProvider');
    }
    return context;
};