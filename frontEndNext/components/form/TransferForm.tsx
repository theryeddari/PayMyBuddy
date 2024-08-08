"use client"

import React, {useEffect, useState} from "react";
import FieldInput from "@/components/FieldInput";
import SubmitButtonDynamic from "@/components/SubmitButton";
import {useAggregation} from "@/util/Context";
import ListEmail from "@/components/aggregationTransfer/ListEmail";
import NumericFieldTransferMaxAmount from "@/components/aggregationTransfer/NumericFieldTransferMaxAmount";
import BoxMessage from "@/components/BoxMessage";
import {useApi} from "@/util/FetchBackEnd";

interface TransferForm {
    children?: React.ReactNode;
}

interface DoTransferResponse {
    messageSuccess: string;
}

const TransferForm: React.FC<TransferForm> = (children) => {

    const {data, error, fetchFromApi} = useApi<DoTransferResponse>(); // Utilisez useApi pour obtenir fetchFromApi
    const {loadData, data: dataAggregation} = useAggregation();
    const [receiverEmail, setReceiverEmail] = useState<string>();
    const [description, setDescription] = useState<string>("Description");
    const [amount, setAmount] = useState<number>();
    const [message, setMessage] = useState<string>("");
    const [isSubmitted, setIsSubmitted] = useState<boolean>(false);

    const handleSubmit = async (event: React.FormEvent<HTMLElement>) => {
        setMessage("");
        setIsSubmitted(true);

        event.preventDefault();
        await fetchFromApi('http://localhost:8080/api/fr/client/dashboard/transfer', {
            method: 'POST',
            body: JSON.stringify({receiverEmail, description, amount}),
        });
        setDescription("Description");
        setAmount(0);
    }

    useEffect(() => {
        if (data) {
            setMessage(data?.messageSuccess as string);
        }
    }, [data, isSubmitted]);

    useEffect(() => {
        setIsSubmitted(false);

    }, [dataAggregation]);


    return (

        <form onSubmit={handleSubmit} className=" w-full flex-row sm:min-w-full sm:w-2/3">
            <div className={"w-full"}>
                {isSubmitted && <BoxMessage onTimeOut={loadData} message={message} error={error}></BoxMessage>}
            </div>
            <div
                className="  pl-2 pr-2 w-full inline-flex justify-center items-end sm:space-x-5 sm:flex-nowrap flex-wrap ">
                <ListEmail onChange={event => setReceiverEmail(event.target.value)}></ListEmail>
                <FieldInput value={description} onChange={event => setDescription(event.target.value)} type={"text"}
                            id={"descriptionTransfer"} name={"descriptionTransfer"}
                            className={"w-full placeholder:font-bold"}></FieldInput>
                <NumericFieldTransferMaxAmount value={amount}
                                               onChange={event => setAmount(parseFloat(event.target.value))}
                                               type={"number"} id={"amountTransfer"} name={"amountTransfer"}
                                               placeholder={"0€"} className={"w-64"}></NumericFieldTransferMaxAmount>
                <SubmitButtonDynamic id={"button"} name={"button"}
                                     className={"ml-3 h-12 sm:py-1.5 !bg-blue-400 hover:!bg-blue-600"}>Transferer</SubmitButtonDynamic>
            </div>
        </form>
    )
}

export default TransferForm