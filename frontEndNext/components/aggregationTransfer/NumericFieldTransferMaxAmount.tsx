"use client"
import {useAggregation} from "@/util/Context";
import {useEffect, useState} from "react";


interface NumericFieldTransferMaxAmount {
    type: string;
    id: string;
    name: string;
    label?: string;
    required?: boolean;
    placeholder?: string;
    className?: string;
    classNameInput?: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    value?: number;

}

const NumericFieldTransferMaxAmount: React.FC<NumericFieldTransferMaxAmount> = ({
                                                                                    type,
                                                                                    id,
                                                                                    name,
                                                                                    label = '',
                                                                                    required = false,
                                                                                    placeholder = '',
                                                                                    className = '',
                                                                                    classNameInput = '',
                                                                                    onChange,
                                                                                    value,

                                                                                }) => {
    const {data} = useAggregation();
    const [maxAmount, setMaxAmount] = useState<number>(0);

    useEffect(() => {
        if (data?.savingClientResponse) {
            setMaxAmount(data.savingClientResponse.saving);
        }
    }, [data]);

    return (
        <div className={className}>
            <label htmlFor={id} className="block text-sm font-bold text-black ">
                {label}
            </label>
            <input
                value={value}
                onChange={onChange}
                type={type}
                id={id}
                name={name}
                required={required}
                placeholder={placeholder}
                min={0.01}
                max={maxAmount}
                step="0.01"
                className={`block placeholder:text-black h-12 mt-1 p-5 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 sm:text-sm ${classNameInput}`}
            />
        </div>
    );
};

export default NumericFieldTransferMaxAmount;