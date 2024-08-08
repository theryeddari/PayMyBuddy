"use client"

import React, {useEffect, useState} from "react";
import {useApi} from "@/util/FetchBackEnd";
import FieldInput from "@/components/FieldInput";
import SubmitButtonDynamic from "@/components/SubmitButton";
import BoxMessage from "@/components/BoxMessage";

interface AddRelationForm {
    children?: React.ReactNode;
}

interface AddRelationShipsResponse {
    messageSuccess: string;
}

const AddRelationForm: React.FC<AddRelationForm> = ({children}) => {

    const {data, error, fetchFromApi} = useApi<AddRelationShipsResponse>();
    const [email, setRelationEmail] = useState<string>('');
    const [message, setMessage] = useState<string>('');


    const handleAddRelation = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setMessage("");
        await fetchFromApi('http://localhost:8080/api/fr/client/dashboard/relationships', {
            method: 'POST',
            body: JSON.stringify({email}),
        });
    };

    useEffect(() => {
        if (data) {
            setMessage(data.messageSuccess)
        }
    }, [data]);

    return (
        <div className={"w-full h-1/4 flex justify-center"}>
            <form onSubmit={handleAddRelation}
                  className="h-full w-full sm:min-w-96 sm:w-1/2 flex flex-col justify-around">
                <div className={"w-full h-10 flex justify-center"}>
                    <BoxMessage error={error} message={message} className={"sm:h-10"}></BoxMessage>
                </div>
                <div className={" w-full h-full pl-2 pr-2 inline-flex justify-center items-start items-top space-x-5"}>
                    <FieldInput type={"email"} id={"emailRelation"} name={"emailRelation"}
                                onChange={event => setRelationEmail(event.target.value)} label={"Chercher une relation"}
                                placeholder={"Saisir une adresse mail"} className={"w-full placeholder:font-bold"}
                                classNameInput={""}></FieldInput>
                    <SubmitButtonDynamic id={"buton"} name={"buton"}
                                         className={"items-end bg-orange-400 hover:bg-orange-600 mt-6 h-11"}>Ajouter</SubmitButtonDynamic>
                </div>
            </form>
        </div>
    )
};

export default AddRelationForm;
