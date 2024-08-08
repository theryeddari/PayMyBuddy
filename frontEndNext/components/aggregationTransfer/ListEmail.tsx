"use client"
import {useAggregation} from "@/util/Context";
import {useEffect, useState} from "react";

interface ListEmail {
    children?: React.ReactNode;
    onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}

const ListEmail: React.FC<ListEmail> = ({onChange, children}) => {
    const {data, isLoading, error} = useAggregation();
    const [selectedEmail, setSelectedEmail] = useState<string>("default");
    const [emailOptions, setEmailOptions] = useState<string[]>([]);

    useEffect(() => {
        if (data?.relationShipsDetailForTransferResponse) {
            setEmailOptions(data.relationShipsDetailForTransferResponse.listFriendsRelationShipsEmail);
        }
    }, [data]);

    const generateOptionList = () => {
        if (error) {
            return <option value="error" className={'bg-red-400'}>{error.message}</option>;
        } else {
            return emailOptions.map((email, index) => (
                <option key={index} value={email}>{email}</option>
            ));
        }
    };

    return (
        <select id="emailSelect" value={selectedEmail} name="email" onChange={(event) => {
            setSelectedEmail(event.target.value);
            onChange ? onChange(event) : null
        }}
                className="block w-full h-12 mt-1 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 sm:text-sm">
            <option value="default" disabled unselectable={"on"}>Selectionnez une relation</option>
            {generateOptionList()}
        </select>
    );
};

export default ListEmail;