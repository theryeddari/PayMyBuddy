// components/FormField.tsx
import React from 'react';

interface FieldInput {
    type: string;
    id: string;
    name: string;
    label?: string;
    required?: boolean;
    placeholder?: string;
    className?: string;
    classNameInput?: string;
    value?: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const FieldInput: React.FC<FieldInput> = ({
                                              type,
                                              id,
                                              name,
                                              label = '',
                                              required = false,
                                              placeholder,
                                              className = '',
                                              classNameInput = '',
                                              value,
                                              onChange,
                                          }) => {
    return (
        <div className={className}>
            <label htmlFor={id} className="block text-sm font-bold text-black">
                {label}
            </label>
            <input
                type={type}
                id={id}
                name={name}
                required={required}
                placeholder={placeholder}
                min={0}
                max={10}
                value={value}
                onChange={onChange}
                className={`block w-full placeholder:text-black h-12 mt-1 px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 sm:text-sm ${classNameInput}`}
            />
        </div>
    );
};

export default FieldInput;