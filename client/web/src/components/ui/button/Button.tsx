interface ButtonProps {
    disabled?: boolean;
    classaName?: string;
    onClick: React.MouseEventHandler<HTMLButtonElement>;
    children: React.ReactNode;
}

export default function Button({
    classaName, 
    disabled = false, 
    onClick, 
    children
}: ButtonProps) {
    return <button onClick={onClick} disabled={disabled} className={classaName}>
     {children}
    </button>
}